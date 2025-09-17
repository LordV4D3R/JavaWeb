package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.EventDTO;
import utils.DBUtils;

/**
 *
 * @author YourName
 */
public class EventDAO {
    
    private static final String GET_ALL_EVENTS = "SELECT id, name, eventDate, location, numOfAttendee, status FROM tblEvent";
    private static final String SEARCH_BY_LOCATION = "SELECT id, name, eventDate, location, numOfAttendee, status FROM tblEvent WHERE location LIKE ?";
    private static final String SEARCH_BY_NAME = "SELECT id, name, eventDate, location, numOfAttendee, status FROM tblEvent WHERE name LIKE ?";
    private static final String SEARCH_BY_ID = "SELECT id, name, eventDate, location, numOfAttendee, status FROM tblEvent WHERE id = ?";
    private static final String GET_ACTIVE_EVENTS = "SELECT id, name, eventDate, location, numOfAttendee, status FROM tblEvent WHERE status = ?";
    private static final String UPDATE_EVENT = "UPDATE tblEvent SET name = ?, eventDate = ?, location = ?, numOfAttendee = ?, status = ? WHERE id = ?";
    private static final String ADD_EVENT = "INSERT INTO tblEvent (id, name, eventDate, location, numOfAttendee, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_EVENT = "DELETE FROM tblEvent WHERE id = ?";
    private static final String COUNT_EVENTS = "SELECT COUNT(*) as total FROM tblEvent";
    private static final String COUNT_ACTIVE_EVENTS = "SELECT COUNT(*) as total FROM tblEvent WHERE status = true";
    
    public List<EventDTO> getAllEvents() throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_EVENTS);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate eventDate = rs.getDate("eventDate") != null ? 
                        rs.getDate("eventDate").toLocalDate() : null;
                    String location = rs.getString("location");
                    int numOfAttendee = rs.getInt("numOfAttendee");
                    boolean status = rs.getBoolean("status");
                    list.add(new EventDTO(id, name, eventDate, location, numOfAttendee, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
    
    public List<EventDTO> searchByLocation(String searchValue) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BY_LOCATION);
                ptm.setString(1, "%" + searchValue + "%");
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate eventDate = rs.getDate("eventDate") != null ? 
                        rs.getDate("eventDate").toLocalDate() : null;
                    String location = rs.getString("location");
                    int numOfAttendee = rs.getInt("numOfAttendee");
                    boolean status = rs.getBoolean("status");
                    list.add(new EventDTO(id, name, eventDate, location, numOfAttendee, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
    
    public List<EventDTO> searchByName(String searchValue) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BY_NAME);
                ptm.setString(1, "%" + searchValue + "%");
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate eventDate = rs.getDate("eventDate") != null ? 
                        rs.getDate("eventDate").toLocalDate() : null;
                    String location = rs.getString("location");
                    int numOfAttendee = rs.getInt("numOfAttendee");
                    boolean status = rs.getBoolean("status");
                    list.add(new EventDTO(id, name, eventDate, location, numOfAttendee, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
    
    public EventDTO findEventById(int eventId) throws SQLException {
        EventDTO event = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BY_ID);
                ptm.setInt(1, eventId);
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate eventDate = rs.getDate("eventDate") != null ? 
                        rs.getDate("eventDate").toLocalDate() : null;
                    String location = rs.getString("location");
                    int numOfAttendee = rs.getInt("numOfAttendee");
                    boolean status = rs.getBoolean("status");
                    event = new EventDTO(id, name, eventDate, location, numOfAttendee, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return event;
    }
    
    public List<EventDTO> getActiveEvents() throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ACTIVE_EVENTS);
                ptm.setBoolean(1, true);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate eventDate = rs.getDate("eventDate") != null ? 
                        rs.getDate("eventDate").toLocalDate() : null;
                    String location = rs.getString("location");
                    int numOfAttendee = rs.getInt("numOfAttendee");
                    boolean status = rs.getBoolean("status");
                    list.add(new EventDTO(id, name, eventDate, location, numOfAttendee, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
    
    public boolean updateEvent(int eventId, String name, LocalDate eventDate, String location, int numOfAttendee, boolean status) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_EVENT);
                ptm.setString(1, name);
                ptm.setDate(2, eventDate != null ? java.sql.Date.valueOf(eventDate) : null);
                ptm.setString(3, location);
                ptm.setInt(4, numOfAttendee);
                ptm.setBoolean(5, status);
                ptm.setInt(6, eventId);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public boolean addEvent(int eventId, String name, LocalDate eventDate, String location, int numOfAttendee) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_EVENT);
                ptm.setInt(1, eventId);
                ptm.setString(2, name);
                ptm.setDate(3, eventDate != null ? java.sql.Date.valueOf(eventDate) : null);
                ptm.setString(4, location);
                ptm.setInt(5, numOfAttendee);
                ptm.setBoolean(6, true); // Default status is true
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public boolean deleteEvent(int eventId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_EVENT);
                ptm.setInt(1, eventId);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    public int getEventCount() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(COUNT_EVENTS);
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return count;
    }
    
    public int getActiveEventCount() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(COUNT_ACTIVE_EVENTS);
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return count;
    }
    
    public boolean isEventIdExists(int eventId) throws SQLException {
        return findEventById(eventId) != null;
    }
}