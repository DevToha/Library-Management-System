package model;

public class DigitalMedia extends Item {
    private String format;
    private double fileSizeMB;

    public DigitalMedia(String id, String title, String format, double fileSizeMB) {
        super(id, title);
        this.format = format;
        this.fileSizeMB = fileSizeMB;
    }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(double fileSizeMB) { this.fileSizeMB = fileSizeMB; }

    @Override
    public String getItemType() {
        return "DigitalMedia";
    }
}