package com.sallyvnge.optionpricingapi.model;

/**
 * Represents the type of an option in the context of financial derivatives.
 *
 * The two types of options are:
 * - CALL: A call option gives the holder the right, but not the obligation, to buy an underlying asset at a specified strike price within a specified time period.
 * - PUT: A put option gives the holder the right, but not the obligation, to sell an underlying asset at a specified strike price within a specified time period.
 */
public enum OptionType {
    CALL,
    PUT
}
