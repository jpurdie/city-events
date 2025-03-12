package com.city.common.util;

import com.city.common.model.entity.EventEntity;
import java.time.ZonedDateTime;
import java.util.List;

public class EventGenerator {

  private static final List<EventEntity> EVENTS =
      List.of(
          createEvent(
              "550e8400-e29b-41d4-a716-446655440000",
              "Tech Innovation Summit",
              "A gathering of the brightest minds in technology.",
              "2024-02-01T10:00:00Z",
              "2024-02-02T10:00:00Z",
              "2024-02-10T09:00:00Z",
              "2024-02-12T17:00:00Z"),
          createEvent(
              "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
              "City Food Festival",
              "Taste the best dishes from world-renowned chefs.",
              "2024-02-05T12:00:00Z",
              "2024-02-06T12:00:00Z",
              "2024-02-15T11:00:00Z",
              "2024-02-17T20:00:00Z"),
          createEvent(
              "70e2a3c7-fd7b-4976-bb89-bc7cdaf2d7a3",
              "Music Fest 2025",
              "A grand celebration of music with top artists.",
              "2024-03-01T08:00:00Z",
              "2024-03-02T08:00:00Z",
              "2024-03-10T18:00:00Z",
              "2024-03-12T23:00:00Z"),
          createEvent(
              "9b3e92d7-5f4c-4db0-8995-1a283e3766c2",
              "Marathon for Charity",
              "Join the city’s biggest marathon for a cause.",
              "2024-04-10T06:30:00Z",
              "2024-04-11T06:30:00Z",
              "2024-04-20T07:00:00Z",
              "2024-04-20T14:00:00Z"),
          createEvent(
              "cc66dabe-f37d-4a5d-914e-238e80a46907",
              "Startup Networking",
              "Meet and connect with investors & entrepreneurs.",
              "2024-05-02T14:00:00Z",
              "2024-05-03T14:00:00Z",
              "2024-05-10T09:00:00Z",
              "2024-05-10T17:00:00Z"),
          createEvent(
              "d1b5f6a9-c8e4-4f2f-9103-bf3cd36f239a",
              "Local Art Exhibition",
              "Discover stunning artworks from local talents.",
              "2024-06-15T09:30:00Z",
              "2024-06-16T09:30:00Z",
              "2024-06-22T10:00:00Z",
              "2024-06-28T19:00:00Z"),
          createEvent(
              "e87dbbcc-3c44-4e92-964d-579a2cd5f17c",
              "Science & Space Expo",
              "Explore the latest advancements in space science.",
              "2024-07-01T11:45:00Z",
              "2024-07-02T11:45:00Z",
              "2024-07-10T10:30:00Z",
              "2024-07-14T18:30:00Z"),
          createEvent(
              "f5cbe2e9-4c75-41b5-b1d6-89bfaecdf54a",
              "AI & Robotics Conference",
              "Learn about the future of artificial intelligence.",
              "2024-08-10T09:00:00Z",
              "2024-08-11T09:00:00Z",
              "2024-08-20T08:00:00Z",
              "2024-08-22T16:00:00Z"),
          createEvent(
              "01db6cd2-ae87-4aa4-97d4-429227f8f6ff",
              "Film & Cinema Awards",
              "An evening celebrating the best in film.",
              "2024-09-05T20:00:00Z",
              "2024-09-06T20:00:00Z",
              "2024-09-15T19:00:00Z",
              "2024-09-15T23:00:00Z"),
          createEvent(
              "3a92485f-cb97-4b26-8653-e5d56fd32f0b",
              "Gaming Championship",
              "Compete in the biggest gaming tournament.",
              "2024-10-12T13:00:00Z",
              "2024-10-13T13:00:00Z",
              "2024-10-20T12:00:00Z",
              "2024-10-22T22:00:00Z"),
          createEvent(
              "07ef5d6d-b7c4-4cf7-84dc-a21b8f26d9b8",
              "Health & Wellness Expo",
              "Discover new ways to stay fit and healthy.",
              "2024-11-03T08:30:00Z",
              "2024-11-04T08:30:00Z",
              "2024-11-12T09:00:00Z",
              "2024-11-14T17:00:00Z"),
          createEvent(
              "53c9956e-0f86-4aa6-81b4-2fd2f36a10d6",
              "Book Fair 2025",
              "Meet your favorite authors and explore books.",
              "2024-12-05T10:00:00Z",
              "2024-12-06T10:00:00Z",
              "2024-12-15T11:00:00Z",
              "2024-12-17T18:00:00Z"),
          createEvent(
              "877a6345-8101-4b3a-bfbf-5e5a6972e0aa",
              "National Science Fair",
              "Showcasing groundbreaking research and discoveries.",
              "2025-01-08T09:00:00Z",
              "2025-01-09T09:00:00Z",
              "2025-01-18T10:00:00Z",
              "2025-01-20T16:00:00Z"),
          createEvent(
              "2e1bf8f0-22fd-43b8-95e5-8bb4e7bb27e3",
              "Winter Carnival",
              "Enjoy winter-themed activities and fun.",
              "2025-02-15T11:00:00Z",
              "2025-02-16T11:00:00Z",
              "2025-02-25T09:30:00Z",
              "2025-02-28T22:00:00Z"),
          createEvent(
              "a20cb689-47aa-47d3-8b1f-93aeb8a86b2d",
              "Digital Marketing Summit",
              "Learn from top experts in digital marketing.",
              "2025-03-10T10:00:00Z",
              "2025-03-11T10:00:00Z",
              "2025-03-20T09:00:00Z",
              "2025-03-22T16:00:00Z"));

  private static EventEntity createEvent(
      String id,
      String name,
      String description,
      String createdAt,
      String updatedAt,
      String startingAt,
      String endingAt) {
    EventEntity event = new EventEntity();
    event.setEventId(id);
    event.setName(name);
    event.setDescription(description);
    event.setCreatedAt(ZonedDateTime.parse(createdAt));
    event.setUpdatedAt(ZonedDateTime.parse(updatedAt));
    event.setStartingAt(ZonedDateTime.parse(startingAt));
    event.setEndingAt(ZonedDateTime.parse(endingAt));
    return event;
  }

  public static List<EventEntity> getEvents(int count) {
    return EVENTS.subList(0, Math.min(count, EVENTS.size()));
  }
}
