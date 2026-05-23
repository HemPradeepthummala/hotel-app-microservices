package org.example.hotelauth.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator{
  @Override
  public String generate() {
      return UUID.randomUUID().toString();
  }
}
