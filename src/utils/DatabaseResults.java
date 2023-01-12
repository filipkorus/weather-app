package utils;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @param message response message
 * @param responseCode HTTP-ish response code indicating success of failure
 * @param dataset dataset collected from database
 */
public record DatabaseResults(String message, int responseCode, DefaultCategoryDataset dataset) {}
