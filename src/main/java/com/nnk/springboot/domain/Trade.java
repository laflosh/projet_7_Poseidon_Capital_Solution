package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "trade")
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tradeId")
	private Integer tradeId;

	@NotNull
	@NotEmpty
	@Column(name = "account")
	private String account;

	@NotNull
	@NotEmpty
	@Column(name = "type")
	private String type;

	@NotNull
	@Column(name = "buyQuantity")
	private Double buyQuantity;

	@Column(name = "sellQuantity")
	private Double sellQuantity;

	@Column(name = "buyPrice")
	private Double buyPrice;

	@Column(name = "sellPrice")
	private Double sellPrice;

	@Column(name = "benchmark")
	private String benchmark;

	@Column(name = "tradeDate")
	private Timestamp tradeDate;

	@Column(name = "security")
	private String security;

	@Column(name = "status")
	private String status;

	@Column(name = "trader")
	private String trader;

	@Column(name = "book")
	private String book;

	@Column(name = "creationName")
	private String creationName;

	@Column(name = "creationDate")
	private Timestamp creationDate;

	@Column(name = "revisionName")
	private String revisionName;

	@Column(name = "revisionDate")
	private Timestamp revsionDate;

	@Column(name = "dealName")
	private String dealName;

	@Column(name = "dealType")
	private String dealType;

	@Column(name = "sourceListId")
	private String souceListId;

	@Column(name = "side")
	private String side;

	//Default constructor
	public Trade() {

	}

	public Trade(String account, String type, Double buyQuantity) {

		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;

	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Double getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevsionDate() {
		return revsionDate;
	}

	public void setRevsionDate(Timestamp revsionDate) {
		this.revsionDate = revsionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSouceListId() {
		return souceListId;
	}

	public void setSouceListId(String souceListId) {
		this.souceListId = souceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
