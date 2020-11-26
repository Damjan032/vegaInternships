import {addCategoryCreator, deleteCategoryCreator, getAllCategoriesCreator,} from './categoriesActionsCreator';
import axios from '../../../axios';

export const getAllCategoriesAction = () => async dispatch => {
    const {data} = await axios.get('/categories');
    dispatch(getAllCategoriesCreator(data));
};

export const addCategoryAction = category => async dispatch => {
    try {
        const {data} = await axios.post('/categories', category);
        let added = {
            id: data,
            dto: category
        };
        dispatch(addCategoryCreator(added));
    } catch (e) {
        if (e.response.status === 400) {
            // dispatch(addClientCreatorFailure(e));
        }
    }
};

export const deleteCategoryAction = categoryId => async dispatch => {
    await axios.delete(`/categories/${categoryId}`);
    dispatch(deleteCategoryCreator(categoryId));
};

