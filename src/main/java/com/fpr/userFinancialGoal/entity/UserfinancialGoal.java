package com.fpr.userFinancialGoal.entity;

import com.fpr.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserfinancialGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finGoalId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate createDate;

    @PrePersist
    private void prePersist(){
        this.createDate = LocalDate.now();
    }

    private String goal;
    private BigDecimal payment;

}
