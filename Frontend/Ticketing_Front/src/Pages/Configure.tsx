"use client";

import React from "react";
import { Button } from "@/components/ui/button";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

interface Props {}

const formSchema = z.object({
  username: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
});

export const ProfileForm = () => {
  // 1. Define your form.
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
    },
  });

  // 2. Define a submit handler.
  function onSubmit(values: z.infer<typeof formSchema>) {
    // Do something with the form values.
    // ✅ This will be type-safe and validated.
    console.log(values);
  }

  return (
    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
      <div>
        <label htmlFor="username" className="block text-sm font-medium">
          Username
        </label>
        <input
          id="username"
          {...form.register("username")}
          className="block w-full border rounded p-2"
        />
        {form.formState.errors.username && (
          <p className="text-red-600 text-sm">
            {form.formState.errors.username.message}
          </p>
        )}
      </div>
      <Button type="submit" variant="secondary">
        Submit
      </Button>
    </form>
  );
};

const Configure = (props: Props) => {
  return (
    <div>
      <Button variant="secondary">This Is Configure</Button>
      {/* Use the ProfileForm Component */}
      <ProfileForm />
    </div>
  );
};

export default Configure;
