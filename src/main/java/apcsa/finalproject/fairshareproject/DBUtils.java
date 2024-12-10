package apcsa.finalproject.fairshareproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeSceneMenuItem(ActionEvent event, String fxmlFile, String title, MenuItem menuItem) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) menuItem.getParentPopup().getOwnerNode()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, User user) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, user.getName());
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, email, currency) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, user.getName());
                psInsert.setString(2, user.getPassword());
                psInsert.setString(3, user.getEmail());
                psInsert.setString(4, user.getCurrency());
                psInsert.executeUpdate();
                FairShare.user = user;
                changeScene(event, "LoggedIn.fxml", "Fair Share");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedEmail = resultSet.getString("email");
                    String retrievedCurrency = resultSet.getString("currency");
                    if (retrievedPassword.equals(password)) {
                        FairShare.user = new User(retrievedCurrency, username, retrievedEmail, retrievedPassword);
                        changeScene(event, "LoggedIn.fxml", "Fair Share");
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Integer getUserId(String user) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer user_id = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            ps = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user_id;
    }

    public static void addFriends(String user1, String user2) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            Integer user1Id = getUserId(user1);
            Integer user2Id = getUserId(user2);
            if (user1Id != null && user2Id != null) {
                psInsert = connection.prepareStatement("INSERT INTO friendships (user1_id, user2_id) VALUES (LEAST(?, ?), GREATEST(?, ?))");
                psInsert.setInt(1, user1Id);
                psInsert.setInt(2, user2Id);
                psInsert.setInt(3, user1Id);
                psInsert.setInt(4, user2Id);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeFriends(String user1, String user2) {
        Connection connection = null;
        PreparedStatement psRemove = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            Integer user1Id = getUserId(user1);
            Integer user2Id = getUserId(user2);
            if (user1Id != null && user2Id != null) {
                psRemove = connection.prepareStatement("DELETE FROM friendships WHERE user1_id = LEAST(?, ?) AND user2_id = GREATEST(?, ?)");
                psRemove.setInt(1, user1Id);
                psRemove.setInt(2, user2Id);
                psRemove.setInt(3, user1Id);
                psRemove.setInt(4, user2Id);
                psRemove.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psRemove != null) {
                try {
                    psRemove.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<String> getFriends(String user) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> friends = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            ps = connection.prepareStatement("SELECT \n" +
                    "    u2.username AS friend_name\n" +
                    "FROM users AS u1\n" +
                    "JOIN friendships AS f\n" +
                    "    ON u1.user_id = f.user1_id OR u1.user_id = f.user2_id\n" +
                    "JOIN users AS u2\n" +
                    "    ON (u2.user_id = f.user1_id AND u2.user_id != u1.user_id)\n" +
                    "    OR (u2.user_id = f.user2_id AND u2.user_id != u1.user_id)\n" +
                    "WHERE u1.username = ?;\n");
            ps.setString(1, user);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    friends.add(rs.getString("friend_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return friends;
    }

    public static ArrayList<String> getNonFriends(String query, String user) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> nonFriends = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            Integer userId = getUserId(user);
            if (userId != null) {
                ps = connection.prepareStatement("SELECT username FROM users WHERE username LIKE ? AND user_id NOT IN (SELECT user1_id FROM friendships WHERE user2_id = ?) AND user_id NOT IN (SELECT user2_id FROM friendships WHERE user1_id = ?) AND user_id <> ?");
                ps.setString(1, query+"%");
                ps.setInt(2, userId);
                ps.setInt(3, userId);
                ps.setInt(4, userId);
                rs = ps.executeQuery();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        nonFriends.add(rs.getString("username"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return nonFriends;
    }

    public static String getUserCurrency(String user) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String currency = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            ps = connection.prepareStatement("SELECT currency FROM users WHERE username = ?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    currency = rs.getString("currency");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return currency;
    }

    public static ArrayList<User> getFriendsAsUsers(String user) {
        ArrayList<String> friends = getFriends(user);
        ArrayList<User> friendsAsUsers = new ArrayList<>();
        for (String friend: friends) {
            friendsAsUsers.add(new User(getUserCurrency(friend), friend));
        }
        return friendsAsUsers;
    }

    public static void saveEvent(String name, Date date, String user) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            Integer userId = getUserId(user);
            if (userId != null && getEventId(name) == null) {
                psInsert = connection.prepareStatement("INSERT INTO events (event_name, event_date, created_by) VALUES (?, ?, ?)");
                psInsert.setString(1, name);
                psInsert.setDate(2, date);
                psInsert.setInt(3, userId);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveTransaction(String user, String event, String transactionName, double amount, String currency, String createdBy) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            Integer userId = getUserId(user);
            Integer createdId = getUserId(createdBy);
            if (userId != null && createdId != null) {
                if (event != null) {
                    Integer eventId = getEventId(event);
                    psInsert = connection.prepareStatement("INSERT INTO transactions (user_id, event_id, transaction_name, amount, currency, created_by) VALUES (?, ?, ?, ?, ?, ?)");
                    psInsert.setInt(1, userId);
                    psInsert.setInt(2, eventId);
                    psInsert.setString(3, transactionName);
                    psInsert.setDouble(4, amount);
                    psInsert.setString(5, currency);
                    psInsert.setInt(6, createdId);
                    psInsert.executeUpdate();
                } else {
                    psInsert = connection.prepareStatement("INSERT INTO transactions (user_id, transaction_name, amount, currency, created_by) VALUES (?, ?, ?, ?, ?)");
                    psInsert.setInt(1, userId);
                    psInsert.setString(2, transactionName);
                    psInsert.setDouble(3, amount);
                    psInsert.setString(4, currency);
                    psInsert.setInt(5, createdId);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Integer getEventId(String event) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer eventId = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fair_share", "liamWhelan", "");
            ps = connection.prepareStatement("SELECT event_id FROM events WHERE event_name = ?");
            ps.setString(1, event);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    eventId = rs.getInt("event_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return eventId;
    }

    private static HashMap<String, ArrayList<Object>> getTransactions(String user) {
        return new HashMap<>();
    }
}
