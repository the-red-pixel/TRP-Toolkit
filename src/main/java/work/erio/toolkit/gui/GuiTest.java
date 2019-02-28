package work.erio.toolkit.gui;

import com.rabbit.gui.component.control.DraggableCamera;
import com.rabbit.gui.show.Show;

public class GuiTest extends Show {
    @Override
    public void setup() {
        super.setup();
        DraggableCamera draggableCamera = new DraggableCamera(10, 10, 200, 200);
        draggableCamera.setEnabled(true);
        registerComponent(draggableCamera);
    }
}
