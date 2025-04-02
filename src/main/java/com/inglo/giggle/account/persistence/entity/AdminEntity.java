package com.inglo.giggle.account.persistence.entity;

import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_admin_account")
)
@DiscriminatorValue("ADMIN")
@DynamicUpdate
public class AdminEntity extends AccountEntity {

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */

    @Builder
    public AdminEntity(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email
    ) {
        super(provider, serialId, password, email, null, null, null, null, null, null);
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.ADMIN;
    }

    @Override
    public String getName() {
        return "관리자";
    }

}

