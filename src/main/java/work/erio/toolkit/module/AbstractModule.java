package work.erio.toolkit.module;

public abstract class AbstractModule {
    protected boolean enabled;

    public AbstractModule(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
