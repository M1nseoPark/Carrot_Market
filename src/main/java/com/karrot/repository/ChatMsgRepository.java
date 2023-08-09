package com.karrot.repository;

import com.karrot.entity.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long>  {
}
