# QuPath-Proportional-Cortex-Segmentation
An automated workflow for QuPath to segment cortical layers based on proportional distance between the **Pia** and **White Matter (WM)** boundaries.

## 📺 Workflow Demo
![Cortical Layering Demo](examples/demo.gif)

## Features
1.Automated Segmentation: Generates Layers 1-6 polygons instantly.
2.Proportional Logic: Uses distance ratios (e.g., 10% for Layer 1) to ensure accuracy across varying cortical thicknesses.
3.Robust Geometry: Utilizes LengthIndexedLine for smooth interpolation even on curved surfaces.

## How to Use
1.Draw a line (Polyline or Brush) along the surface → Classify as "Pia".
2.Draw a line along the white matter boundary → Classify as "WM".
3.Run Script:
Open scripts/Cortical_Layer_Interpolation.groovy in QuPath's script editor. Click Run.
4.Adjust (Optional):
Modify the fractions array in the script to change layer thicknesses (e.g., 0.10 for 10% depth).

📂 Repository Structure
/scripts: The core Groovy implementation.
/examples: Screenshots of before/after segmentation.
.gitignore: Excludes bulky QuPath project data.

⚖️ License
This project is licensed under the MIT License.

📧 Contact
Created by MeoWareLi. Feel free to open an Issue for questions or suggestions!
