package softuni.exam.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team {



    //•	id – integer number, primary identification field.
    //•	first_name – a string (required).
    //•	last_name – a string (required) between 3 and 15 characters.
    //•	number – a Integer (required) between 1 and 99.
    //•	salary – a Bigdecimal (required) min 0.
    //•	position – a ENUM (required).
    //•	picture – a Picture entity (required).
    //•	team
}
