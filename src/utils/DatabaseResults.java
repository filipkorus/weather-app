package utils;

import org.jfree.data.category.DefaultCategoryDataset;

public record DatabaseResults(String message, int responseCode, DefaultCategoryDataset data) {}
