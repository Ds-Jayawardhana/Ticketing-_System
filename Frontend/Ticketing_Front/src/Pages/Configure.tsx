import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Button } from '@/components/ui/button';

const EventTicketingSystem = () => {
  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle className="text-xl font-semibold">
          Event Ticketing System Demo
        </CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="totalTickets">Total Tickets</Label>
            <Input 
              id="totalTickets"
              type="number" 
              placeholder="1000"
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="releaseRate">Release Rate (per second)</Label>
            <Input 
              id="releaseRate"
              type="number" 
              placeholder="5"
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="retrievalRate">Retrieval Rate (per second)</Label>
            <Input 
              id="retrievalRate"
              type="number" 
              placeholder="3"
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="maxCapacity">Max Capacity</Label>
            <Input 
              id="maxCapacity"
              type="number" 
              placeholder="100"
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="vendors">Number of Vendors</Label>
            <Input 
              id="vendors"
              type="number" 
              placeholder="3"
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="customers">Number of Customers</Label>
            <Input 
              id="customers"
              type="number" 
              placeholder="5"
              className="w-full"
            />
          </div>

          <Button className="w-full mt-6" size="lg">
            CONFIGURE
          </Button>
        </div>
      </CardContent>
    </Card>
  );
};

export default EventTicketingSystem;