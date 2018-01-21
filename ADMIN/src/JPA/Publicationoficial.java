package JPA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "PUBLICATIONS_TABLE")

public class Publicationoficial implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PUBLICATIONID")
	private Long id;
	private String title;
	private String authors;
	private String pubdate;

	public Publicationoficial() {
	}
	
	public Long getId() {
		return id;
	}

	public void setid(long id) {
		this.id = id;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getauthors() {
		return authors;
	}

	public void setauthors(String authors) {
		this.authors = authors;
	}

	public String getpubdate() {
		return pubdate;
	}

	public void setpubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);

		return hash;
	}

	@Override

	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Publicationoficial)) {
			return false;
		}
		Publicationoficial other = (Publicationoficial) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Publication " + id + ": " + title;
	}
}
