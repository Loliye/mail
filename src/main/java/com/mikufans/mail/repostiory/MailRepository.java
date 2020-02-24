package com.mikufans.mail.repostiory;

import com.mikufans.mail.entity.OaEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<OaEmail,Integer>
{
}
