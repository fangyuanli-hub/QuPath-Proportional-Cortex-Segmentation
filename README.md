# QuPath-Proportional-Cortex-Segmentation
An automated workflow for QuPath to segment cortical layers based on proportional distance between the **Pia** and **White Matter (WM)** boundaries.

📌 Features
Automated Segmentation: Generates Layers 1-6 polygons instantly.

Proportional Logic: Uses distance ratios (e.g., 10% for Layer 1) to ensure accuracy across varying cortical thicknesses.

Robust Geometry: Utilizes LengthIndexedLine for smooth interpolation even on curved surfaces.

🚀 How to Use
Draw Boundaries:

Draw a line (Polyline or Brush) along the surface → Classify as "Pia".

Draw a line along the white matter boundary → Classify as "WM".

Run Script:

Open scripts/Cortical_Layer_Interpolation.groovy in QuPath's script editor.

Click Run.

Adjust (Optional):

Modify the fractions array in the script to change layer thicknesses (e.g., 0.10 for 10% depth).

📂 Repository Structure
/scripts: The core Groovy implementation.

/examples: Screenshots of before/after segmentation.

.gitignore: Excludes bulky QuPath project data.

⚖️ License
This project is licensed under the MIT License.

📧 Contact
Created by MeoWareLi. Feel free to open an Issue for questions or suggestions!
