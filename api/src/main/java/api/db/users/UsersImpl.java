package api.db.users;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import api.db.users.generated.GeneratedUsersImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 * The default implementation of the {@link api.db.users.Users}-interface.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code generator.
 * 
 * @author company
 */
@JsonIgnoreProperties("password")
public final class UsersImpl extends GeneratedUsersImpl implements Users {
  private static final long serialVersionUID = -7552975849070084309L;

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return createAuthorityList(getRole());
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }
}