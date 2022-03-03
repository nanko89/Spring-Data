package Ex2SalesDatabase;

import entity.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BasicEntity {

    @Column(name = "location_Name", nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class)
    private Set<Sale> sales;


    public StoreLocation() {
    }

    public StoreLocation(String locationName) {
        this.locationName = locationName;
        this.sales = new HashSet<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
