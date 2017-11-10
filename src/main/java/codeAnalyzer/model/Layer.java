package codeAnalyzer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* Kontroler, servis, repozitorijum ili neki drugi sloj... nivo = 2*/

@Entity
@Table(name="layer")
public class Layer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name", nullable=false)
	private String name;
	@ManyToOne(optional = false)
	private ComponentSide side;
	
	public Layer() {
		
	}
	public Layer(String name, ComponentSide side) {
		this.name = name;
		this.side = side;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ComponentSide getSide() {
		return side;
	}
	public void setSide(ComponentSide side) {
		this.side = side;
	}
}
