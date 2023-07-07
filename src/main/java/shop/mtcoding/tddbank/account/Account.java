package shop.mtcoding.tddbank.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import shop.mtcoding.tddbank._core.erros.exception.Exception400;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.erros.exception.Exception403;
import shop.mtcoding.tddbank.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Table(name = "account_tb", indexes = {
        @Index(name = "idx_account_number", columnList = "number")
})
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true, nullable = false, length = 4)
    private Integer number; // 계좌번호
    @Column(nullable = false, length = 4)
    private Integer password; // 계좌비번
    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 1000원)

    @Column(nullable = false)
    private Boolean status; // true, false

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Account(Long id, User user, Integer number, Integer password, Long balance, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkOwner(Long userId) {
        if (user.getId().longValue() != userId.longValue()) {
            throw new Exception403("계좌 소유자가 아닙니다");
        }
    }

    public void deposit(Long amount) {
        balance = balance + amount;
    }

    public void checkSamePassword(Integer password) {
        if (!this.password.equals(password)) {
            throw new Exception401("계좌 비밀번호 검증에 실패했습니다");
        }
    }

    public void checkBalance(Long amount) {
        if (this.balance < amount) {
            throw new Exception400("amount","계좌 잔액이 부족합니다");
        }
    }

    public void withdraw(Long amount) {
        balance = balance - amount;
    }
}