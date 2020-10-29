package com.leyou.pojo;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class CategoryBrandKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_category_brand.category_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    private Long categoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_category_brand.brand_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    private Long brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_category_brand
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_category_brand.category_id
     *
     * @return the value of tb_category_brand.category_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_category_brand.category_id
     *
     * @param categoryId the value for tb_category_brand.category_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_category_brand.brand_id
     *
     * @return the value of tb_category_brand.brand_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_category_brand.brand_id
     *
     * @param brandId the value for tb_category_brand.brand_id
     *
     * @mbggenerated Wed Aug 26 13:00:16 CST 2020
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}