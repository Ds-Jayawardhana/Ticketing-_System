import React from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';

const HomePage = () => {
  return (
    <div className="min-h-screen flex flex-col items-center bg-background p-8">
      {/* Greeting Section */}
      <div className="text-center mb-16 mt-12">
        <h1 className="text-4xl font-bold mb-2">Welcome to Ticket System</h1>
        <p className="text-lg text-muted-foreground">
          Please select your role to continue
        </p>
      </div>

      {/* Buttons Card */}
      <Card className="w-full max-w-md">
        <CardContent className="flex flex-col gap-4 p-6">
          <Button 
            className="h-16 text-lg" 
            variant="default"
          >
            Admin
          </Button>
          
          <Button 
            className="h-16 text-lg"
            variant="default"
          >
            Customer
          </Button>
          
          <Button 
            className="h-16 text-lg"
            variant="default"
          >
            Vendor
          </Button>
        </CardContent>
      </Card>
    </div>
  );
};

export default HomePage;