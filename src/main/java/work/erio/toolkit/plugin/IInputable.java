package work.erio.toolkit.plugin;

public interface IInputable {
    int getInputCount();

    void onInputChange(int id, String newVal);
}
