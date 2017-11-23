package api.db.users;

import api.db.users.generated.GeneratedUsers;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The main interface for entities of the {@code users}-table in the database.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code generator.
 * 
 * @author company
 */
public interface Users extends GeneratedUsers, UserDetails {}