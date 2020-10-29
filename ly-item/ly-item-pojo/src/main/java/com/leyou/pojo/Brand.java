package com.leyou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;



@Data
@AllArgsConstructor
public class Brand implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_brand.id
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_brand.name
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_brand.image
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_brand.letter
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    private String letter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_brand
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    private static final long serialVersionUID = 1L;

    public Brand(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_brand.id
     *
     * @return the value of tb_brand.id
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_brand.id
     *
     * @param id the value for tb_brand.id
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_brand.name
     *
     * @return the value of tb_brand.name
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_brand.name
     *
     * @param name the value for tb_brand.name
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_brand.image
     *
     * @return the value of tb_brand.image
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_brand.image
     *
     * @param image the value for tb_brand.image
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_brand.letter
     *
     * @return the value of tb_brand.letter
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public String getLetter() {
        return letter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_brand.letter
     *
     * @param letter the value for tb_brand.letter
     *
     * @mbggenerated Mon Aug 24 12:01:01 CST 2020
     */
    public void setLetter(String letter) {
        this.letter = letter == null ? null : letter.trim();
    }
}