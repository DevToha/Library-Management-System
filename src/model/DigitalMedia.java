package model;

public class DigitalMedia extends Item {

    private String format;
    private double fileSize;

    public DigitalMedia(String id, String title, String category, String format, double fileSize) {
        super(id, title, category);
        this.format = format;
        this.fileSize = fileSize;
    }

    @Override
    public String getType() {
        return "DigitalMedia";
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return daysLate * 3.0;
    }

    public String getFormat() {
        return format;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
}