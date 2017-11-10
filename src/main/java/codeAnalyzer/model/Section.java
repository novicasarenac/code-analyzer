package codeAnalyzer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* Sekcija, nivo = 5*/

@Entity
@Table(name="section")
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="code", nullable=false)
	private String code;
	@Column(name="start_line", nullable=false)
	private int startLine;
	@Column(name="end_line", nullable=false)
	private int endLine;
	@Column(name="in_method", nullable=false)
	private boolean inMethod;
	@ManyToOne(optional = false)
	private Method method;
	
	public Section() {
		
	}
	public Section(String code, int startLine, int endLine, Method method) {
		this.code = code;
		this.startLine = startLine;
		this.endLine = endLine;
		this.method = method;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStartLine() {
		return startLine;
	}
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}
	public int getEndLine() {
		return endLine;
	}
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
}
