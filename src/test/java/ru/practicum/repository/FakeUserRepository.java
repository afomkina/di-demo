package ru.practicum.repository;

public class FakeUserRepository implements UserRepository {
    @Override
    public void save(String email) {
        System.out.println("FAKE: saving user " + email);
    }
}
