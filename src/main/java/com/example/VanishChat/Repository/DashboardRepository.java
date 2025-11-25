package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.DashboardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardUser, String> {}