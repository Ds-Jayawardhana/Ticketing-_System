import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { LineChart, Line, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Play, Square } from 'lucide-react';
import { useState,useEffect } from 'react';


const chartData = [
  { time: '0s', customerRate: 4, vendorRate: 5 },
  { time: '1s', customerRate: 3, vendorRate: 4 },
  { time: '2s', customerRate: 6, vendorRate: 3 },
  { time: '3s', customerRate: 4, vendorRate: 6 },
  { time: '4s', customerRate: 8, vendorRate: 7 },
  { time: '5s', customerRate: 5, vendorRate: 5 }
];

const AdminDashboard = () => {

  const [totalTicket,setTotalTicket]=useState('0');
  useEffect(() => {
    fetch('http://localhost:8080/configure/totTickets')
      .then(response => response.text())
      .then(data => {
        setTotalTicket(Number(data).toLocaleString());
      })
      .catch(error => console.error('Error fetching total tickets:', error)); // Optional error handling
  }, []);
  return (
    <div className="p-6 space-y-6 bg-background">
      {/* Top Row - Metric Cards */}
      <div className="grid gap-4 md:grid-cols-3">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Tickets</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
              <circle cx="9" cy="7" r="4" />
              <path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{totalTicket}</div>
            <p className="text-xs text-muted-foreground">
              Initial pool size
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Tickets Released</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">346</div>
            <p className="text-xs text-muted-foreground">
              +23 since last check
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Tickets Purchased</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <rect width="20" height="14" x="2" y="5" rx="2" />
              <path d="M2 10h20" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">284</div>
            <p className="text-xs text-muted-foreground">
              +15 in last hour
            </p>
          </CardContent>
        </Card>
      </div>

      {/* Middle Row - Chart and Logs */}
      <div className="grid gap-4 md:grid-cols-2">
        {/* Chart */}
        <Card className="col-span-1">
          <CardHeader>
            <CardTitle>Customer vs Vendor Rate</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[200px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={chartData}>
                  <XAxis dataKey="time" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line 
                    type="monotone" 
                    dataKey="customerRate" 
                    stroke="#8884d8" 
                    strokeWidth={2}
                    name="Customer Retrieval Rate" 
                  />
                  <Line 
                    type="monotone" 
                    dataKey="vendorRate" 
                    stroke="#82ca9d" 
                    strokeWidth={2}
                    name="Vendor Release Rate"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>

        {/* Logs */}
        <Card className="col-span-1">
          <CardHeader>
            <CardTitle>Activity Log</CardTitle>
          </CardHeader>
          <CardContent>
            <ScrollArea className="h-[200px] w-full rounded-md border p-4">
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <Avatar className="h-8 w-8">
                      <AvatarFallback>V1</AvatarFallback>
                    </Avatar>
                    <div>
                      <p className="text-sm font-medium">Vendor 1</p>
                      <p className="text-xs text-muted-foreground">Released 5 tickets</p>
                    </div>
                  </div>
                  <span className="text-xs text-muted-foreground">2s ago</span>
                </div>

                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <Avatar className="h-8 w-8">
                      <AvatarFallback>C1</AvatarFallback>
                    </Avatar>
                    <div>
                      <p className="text-sm font-medium">Customer 2</p>
                      <p className="text-xs text-muted-foreground">Purchased 2 tickets</p>
                    </div>
                  </div>
                  <span className="text-xs text-muted-foreground">5s ago</span>
                </div>
              </div>
            </ScrollArea>
          </CardContent>
        </Card>
      </div>

      {/* Bottom Row - Control Buttons */}
      <div className="flex justify-center gap-4">
        <Button size="lg" className="w-32 h-12 bg-[#22c55e] hover:bg-[#15803d]" variant="default">
          <Play className="mr-2 h-5 w-5" /> Start
        </Button>
        <Button size="lg" className="w-32 h-12" variant="destructive">
          <Square className="mr-2 h-5 w-5" /> Stop
        </Button>
      </div>
    </div>
  );
};

export default AdminDashboard;