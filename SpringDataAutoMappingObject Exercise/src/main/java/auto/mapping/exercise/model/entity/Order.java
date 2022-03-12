package auto.mapping.exercise.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
        private User buyer;
    @ManyToMany
        private Set<Game> games;

    public Order() {
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
