package com.inglo.giggle.account.domain;

import com.inglo.giggle.security.domain.mysql.Account;
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
public class Admin extends Account {

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */

    @Builder
    public Admin(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            Boolean marketingAllowed,
            Boolean notificationAllowed
    ) {
        super(provider, serialId, password, email, profileImgUrl, phoneNumber, marketingAllowed, notificationAllowed);
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

