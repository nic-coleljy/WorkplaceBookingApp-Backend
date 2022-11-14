package com.collab.g5.demo.news;

import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int nid;

    private LocalDate date;

    @NotNull(message = "Title should not be null")
    @Size(min = 5, message = "Title should be at least 5 characters")
    private String title;
    private String content;

    @PersistenceConstructor
    public News(int nid, LocalDate date, String title, String content)
    {
        this.nid = nid;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    @PersistenceConstructor
    public News(String content)
    {
        this.title = title;
    }

    //foreign keys

    @ManyToOne
    @JoinColumn(name="company_cid",foreignKey = @ForeignKey(name="fk1_news"))
    private Company company;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_userEmail",foreignKey = @ForeignKey(name="fk2_news"))
    private User user;

}
