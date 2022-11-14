package com.collab.g5.demo.regulations;

import com.collab.g5.demo.companies.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class RegulationLimit {
    @EmbeddedId
    private RegulationLimitKey regulationLimitKey;

    @ManyToOne
    @JsonIgnore
    @MapsId("startDate")
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "regulation_start_Date", foreignKey = @ForeignKey(name = "fk1_regulationLimit"))
    private Regulation regulation;


    @ManyToOne
    @JsonIgnore
    @MapsId("cid")
    @JoinColumn(name = "company_cid", foreignKey = @ForeignKey(name = "fk2_regulationLimit"))
    private Company company;

    @Min(0)
    @Max(100)
    @PositiveOrZero
    @NotNull(message = "dailyLimit should not be null")
    private int dailyLimit;

    public RegulationLimit(RegulationLimitKey regulationLimitKey, int dailyLimit) {
        this.regulationLimitKey = regulationLimitKey;
        this.dailyLimit = dailyLimit;
    }
}
