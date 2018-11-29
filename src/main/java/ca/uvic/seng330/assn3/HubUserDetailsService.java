package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class HubUserDetailsService implements UserDetailsService {

  // get user from the database, via Hibernate
  @Autowired private UserRepository users;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

    ca.uvic.seng330.assn3.models.User user = users.findByUsername(username).get(0);

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("USER"));
    if (user.getIsAdmin()) {
      authorities.add(new SimpleGrantedAuthority("ADMIN"));
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), true, true, true, true, authorities);
  }
}
