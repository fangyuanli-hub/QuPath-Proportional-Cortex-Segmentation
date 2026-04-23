/**
 * Automated Cortical Layer Segmentation via Linear Interpolation
 * * Instructions:
 * 1. Draw a line along the cortical surface and classify it as "Pia".
 * 2. Draw a line along the white matter boundary and classify it as "WM".
 * 3. Run this script.
 * * The script will generate polygons for Layers 1-6 based on the proportional
 * distance between the two defined boundaries.
 *
 * @author MeoWareLi
 */

import org.locationtech.jts.linearref.LengthIndexedLine
import qupath.lib.roi.ROIs
import qupath.lib.objects.PathObjects

// Initialize hierarchy
def hierarchy = getCurrentHierarchy()
def allAnnos = hierarchy.getAnnotationObjects()

// Find Pia and WM lines (Case-insensitive search)
def pia = allAnnos.find { it.getPathClass() != null && it.getPathClass().getName().equalsIgnoreCase("Pia") }
def wm = allAnnos.find { it.getPathClass() != null && it.getPathClass().getName().equalsIgnoreCase("WM") }

if (pia == null || wm == null) {
    println "❌ Error: Could not find 'Pia' or 'WM' annotations. Please check your classification names!"
    return
}

println "✅ Found Pia and WM boundaries. Calculating layers..."

// Get Geometries and Image Plane
def geomPia = pia.getROI().getGeometry()
def geomWm = wm.getROI().getGeometry()
def plane = pia.getROI().getImagePlane()

// ==========================================================
// Define Cumulative Ratios (from 0.0 to 1.0)
// Adjust these percentages based on your brain region
// ==========================================================
def fractions = [0.0, 0.10, 0.40, 0.50, 0.70, 1.0]
def layerNames = ["Layer 1", "Layer 2/3", "Layer 4", "Layer 5", "Layer 6"]

def numPoints = 100 // Sampling precision (Higher = smoother curves)
def lengthIndexPia = new LengthIndexedLine(geomPia)
def lengthIndexWm = new LengthIndexedLine(geomWm)

def lengthPia = geomPia.getLength()
def lengthWm = geomWm.getLength()

// Iterate to generate layer polygons
for (int j = 0; j < layerNames.size(); j++) {
    def fracTop = fractions[j]
    def fracBottom = fractions[j+1]
    
    def ptsX = []
    def ptsY = []

    // 1. Generate points for the top boundary of the layer
    for (int i = 0; i <= numPoints; i++) {
        def t = i / (double)numPoints
        def cPia = lengthIndexPia.extractPoint(t * lengthPia)
        def cWm = lengthIndexWm.extractPoint(t * lengthWm)
        
        ptsX << (cPia.x + fracTop * (cWm.x - cPia.x))
        ptsY << (cPia.y + fracTop * (cWm.y - cPia.y))
    }

    // 2. Generate points for the bottom boundary (Reverse order to close polygon)
    for (int i = numPoints; i >= 0; i--) {
        def t = i / (double)numPoints
        def cPia = lengthIndexPia.extractPoint(t * lengthPia)
        def cWm = lengthIndexWm.extractPoint(t * lengthWm)
        
        ptsX << (cPia.x + fracBottom * (cWm.x - cPia.x))
        ptsY << (cPia.y + fracBottom * (cWm.y - cPia.y))
    }

    // 3. Create and add the Annotation object
    def roi = ROIs.createPolygonROI(ptsX as double[], ptsY as double[], plane)
    def layerAnnotation = PathObjects.createAnnotationObject(roi, getPathClass(layerNames[j]))
    
    hierarchy.addPathObject(layerAnnotation)
}

println "🎉 Cortical layers generated successfully!"