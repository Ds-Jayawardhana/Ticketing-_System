import { ThemeProvider } from "./components/theme-provider"
import { Button } from "./components/ui/button"

function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <Button>Button</Button>
    </ThemeProvider>
  )
}

export default App
