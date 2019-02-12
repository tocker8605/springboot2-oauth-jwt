package my.tocker.jwtauth.database.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "user_name", length = 20, nullable = false, unique = true)
	private String userName;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	//1:수퍼관리자, 2:관리자, 3:사용자
	@Column(name = "user_type", length = 1, nullable = false)
	private String userType;
	
	@Column(name = "reg_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate = new Date();
}
