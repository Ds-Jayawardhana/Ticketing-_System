
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { useNavigate } from 'react-router-dom';




const HomePage = () => {

  const navigate=useNavigate();

  const handleAdminClick=()=>{
    navigate('/Configure');
  }
  const handleCustomerClick=()=>{
    navigate('/Customer');
  }
  const handleVendorClick=()=>{
    navigate('/Vendor');
  }

  return (
    <div className="min-h-screen flex flex-col items-center bg-background p-8">
    
      <div className="text-center mb-16 mt-12">
        <h1 className="text-4xl font-bold mb-2">Welcome to Real Time Ticketing System</h1>
        <p className="text-lg text-muted-foreground">
          Please select your role to continue
        </p>
      </div>

    
      <Card className="w-full max-w-md">
        <CardContent className="flex flex-col gap-4 p-6">
          <Button 
            className="h-16 text-lg" 
            variant="default"
          onClick={handleAdminClick}>
            Admin
          </Button>
          
          <Button 
            className="h-16 text-lg"
            variant="default"
            onClick={handleCustomerClick}
          >
            Customer
          </Button>
          
          <Button 
            className="h-16 text-lg"
            variant="default"
            onClick={handleVendorClick}
          >
            Vendor
          </Button>
        </CardContent>
      </Card>
    </div>
  );
};

export default HomePage;