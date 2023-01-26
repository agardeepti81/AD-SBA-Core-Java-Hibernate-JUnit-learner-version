package sba.sms.models;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

@NamedQueries({
	@NamedQuery(name="validateStudent",query="From Student s where s.email=:email and s.password=:password")
})
@Entity
@Table(name="student")
public class Student {
	@Id
	@Column(columnDefinition = "varchar(50)")
	String email;
	@NonNull
	@Column(columnDefinition = "varchar(50)")
	String name;
	@NonNull
	@Column(columnDefinition = "varchar(50)")
	String password;
	@ToString.Exclude
	@OneToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinTable(name = "student_courses",
	joinColumns = @JoinColumn(name="student_email"),
	inverseJoinColumns = @JoinColumn(name="courses_id"))
	List<Course> courses;
	
	//Constructor
	public Student(String email, @NonNull String name, @NonNull String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(courses, email, name, password);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	public void addCourse(Course c) {
		this.courses.add(c);
	}

}
