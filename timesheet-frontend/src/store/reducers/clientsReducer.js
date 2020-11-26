export function clients(state = [], action) {
  switch (action.type) {
    case 'ADD_CLIENT':
      return [...state, action.client];

    case 'GET_ALL_CLIENTS':
      return action.clients;

    case 'DELETE_CLIENT':
      return state.filter(item => item.id !== action.clientId);

    case 'UPDATE_CLIENT':

      state = [
        ...state.filter(c => c.id !== action.client.id),
        action.client
      ];

      return state;

    case 'ADD_CLIENT_FAILURE':

      console.log(action.error.response);
      break;

    case 'SEARCH_CLIENTS':
      return action.clients;

    default:
      return state;
  }
}
