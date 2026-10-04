// Rohan Chaudhari
package com.studentScholar.users;

class User {
    private String userId;
    private String name;

    protected User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean login(String password) {
        return false;
    }

    public void logout() {
    }
}
