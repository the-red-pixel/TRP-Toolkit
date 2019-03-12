package work.erio.toolkit.plugin;

public interface IInputable {
    int getInputCount();

    void onInputChange(int index, String newVal, String oldVal);
}
