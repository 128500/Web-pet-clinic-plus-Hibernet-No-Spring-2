package com.llisovichok.lessons.clinic;

import java.io.Serializable;

import javax.persistence.*;

/**
*This class represents a client's pet
*/

@Entity
@Table(name = "PETS_T")
public class Pet implements Serializable {

	@Id @ GeneratedValue
	private Integer id;

	@Column(name = "nickname")
	private String name;

	@Column(name  = "kind")
	private String kind = "pet";

	@Column(name  = "age")
	private int age;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "pet_photo_id")
	private PetPhoto photo;

	/**
	 * Detailed information about the pet, containing health problems description
	 */
	//private DetailedInfo detailedInfo;

	public Pet(){}

	public Pet(final String name){
		this.name = name;
	}

	public Pet(final String name, final String kind){
		this.name = name;
		this.kind = kind;
	}

	public Pet(final String name, final String kind, final int age){
		this.name = name;
		this.kind = kind;
		this.age = age;
	}

	public Pet(final String name, final String kind, final int age, final DetailedInfo df){
		this.name = name;
		this.kind = kind;
		this.age = age;
	}


	public Integer getId() {
		return id;
	}

	public String getName(){
		return this.name;
	}

	public String getKind(){
		return this.kind;
	}

	public int getAge(){
		return this.age;
	}

	public PetPhoto getPhoto() {
		return photo;
	}

	public void setPhoto(PetPhoto photo) {
		this.photo = photo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAge(final int age){
		this.age = age;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setKind(final String kind){
		this.kind =kind;
	}

	/**public DetailedInfo getDetailedInfo(){
		return this.detailedInfo;
	}*/

	/**public void setDetailedInfo(final  DetailedInfo detailedInfo) {
		this.detailedInfo = detailedInfo;
	}*/

	@Override
	public String toString() {
		return "Pet{" +
				"id=" + id +
				", name='" + name + '\'' +
				", kind='" + kind + '\'' +
				", age=" + age +
				", photo=" + photo +
				'}';
	}
}