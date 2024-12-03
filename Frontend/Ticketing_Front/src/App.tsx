import { Outlet } from "react-router-dom";
import { ThemeProvider } from "@/components/theme-provider";
import './App.css'

function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <div>
        {/* Optional: Add navigation or layout components here */}
        <Outlet /> {/* This renders the child routes */}
      </div>
    </ThemeProvider>
  );
}

export default App;
