import { createContext } from 'react'

export const userContext = createContext(JSON.parse(sessionStorage.getItem("User")));
