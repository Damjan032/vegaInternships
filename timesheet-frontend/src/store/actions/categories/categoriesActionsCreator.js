export const addCategoryCreator = category => ({
    type: 'ADD_CATEGORY',
    category,
});

export const getAllCategoriesCreator = categories => ({
    type: 'GET_ALL_CATEGORIES',
    categories,
});

export const deleteCategoryCreator = categoryId => ({
    type: 'DELETE_CATEGORY',
    categoryId,
})

export const updateCategoryCreator = category => ({
    type: 'UPDATE_CATEGORY',
    category,
})