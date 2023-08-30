package ztm.section8.queue;

public class Topic {
    private String name;
    private int partitions;

    public Topic(String name, int partitions) {
        this.name = name;
        this.partitions = partitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartitions() {
        return partitions;
    }

    public void setPartitions(int partitions) {
        this.partitions = partitions;
    }
}
