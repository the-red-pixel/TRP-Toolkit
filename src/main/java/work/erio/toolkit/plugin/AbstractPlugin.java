package work.erio.toolkit.plugin;

public abstract class AbstractPlugin {
    private String name;
    private String description = "#no description#";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
