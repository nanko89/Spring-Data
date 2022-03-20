package com.example.jsonexercisecardealer.models.dto.seed;

import com.google.gson.annotations.Expose;

public class SupplierSeedDTO {
    @Expose
    private String name;
   @Expose
    private boolean isImporter;

    public SupplierSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}

